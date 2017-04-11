# class Upload
#   include Mongoid::Document
#   field :name, type: String
#   field :attachment, type: String
# end

class Upload < ActiveRecord::Base
	mount_uploader :attachment, AttachmentUploader
	validates :name, presence: true # Make sure the owner's name is present.
end