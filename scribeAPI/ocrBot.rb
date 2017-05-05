# Bot to place OCR result from Tesseract (port 8080) into Scribe workflow

# USEAGE
# ruby ocrBot.rb --image_url=<IMAGE URL>
#                --x_val=<TOP LEFT X COORDINATE> --y_val=<TOP LEFT Y COORDINATE>
#                --width=<WIDTH> --height=<WIDTH>
#                --text_value=<TEXT INSIDE BOX>
#                --scribe_endpoint="http://ec2-54-173-153-28.compute-1.amazonaws.com:8000/classifications"

# Note: script starts at bottom

require 'open-uri'
require 'json'
require 'cgi'

class Hash
  def to_params
    params = ''
    stack = []

    each do |k, v|
      if v.is_a?(Hash)
        stack << [k,v]
      elsif v.is_a?(Array)
        stack << [k,Hash.from_array(v)]
      else
        params << "#{k}=#{v}&"
      end
    end

    stack.each do |parent, hash|
      hash.each do |k, v|
        if v.is_a?(Hash)
          stack << ["#{parent}[#{k}]", v]
        else
          params << "#{parent}[#{k}]=#{v}&"
        end
      end
    end

    params.chop!
    params
  end

  def self.from_array(array = [])
    h = Hash.new
    array.size.times do |t|
      h[t] = array[t]
    end
    h
  end

end

class ScribeBot
  def initialize(scribe_endpoint)
    @classifications_endpoint = scribe_endpoint
  end

  def classify_subject_by_id(subject_id, workflow_name, task_key, data)
    params = {
      workflow: {
        name: workflow_name
      },
      classifications: {
        annotation: data,
        task_key: task_key,
        subject_id: subject_id
      }
    }
    submit_classification params
  end

  def classify_subject_by_url(subject_url, workflow_name, task_key, data)
    params = {
      subject: {
        location: {
          standard: CGI::escape(subject_url)
        }
      },
      workflow: {
        name: workflow_name
      },
      classifications: {
        annotation: data,
        task_key: task_key
      }
    }
    submit_classification params
  end
  
  def submit_classification(params)
    require 'uri'
    require "net/http"

    uri = URI(@classifications_endpoint)

    req = Net::HTTP::Post.new(uri.path, {'BOT_AUTH' => ENV['SCRIBE_BOT_TOKEN']})
    req.body = params.to_params
    http = Net::HTTP.new(uri.host, uri.port)

    response = http.start {|http| http.request(req) }

    begin
      JSON.parse response.body
    rescue
      nil
    end
  end
end


# Start of script!

options = Hash[ ARGV.join(' ').scan(/--?([^=\s]+)(?:=(\S+))?/) ]
args = ARGV.select { |a| ! a.match /^-/ }
bot = ScribeBot.new options["scribe_endpoint"]

classification = bot.classify_subject_by_url( options["image_url"], "mark", "mark_primary", {
                                                x: options["x_val"],
                                                y: options["y_val"],
                                                width: options["width"],
                                                height: options["height"],
                                                subToolIndex: 0
                                              })['classification']
puts "Created classification: #{classification.to_json}"
mark_id = classification['child_subject']['id']
transcribe_task_key = classification['child_subject']['type']
classification = bot.classify_subject_by_id( mark_id, "transcribe", transcribe_task_key, { value: options['text_value'] })
puts "Created transcription classification: #{classification.to_json}"
