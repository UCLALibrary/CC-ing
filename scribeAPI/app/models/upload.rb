class Upload < ActiveRecord::Base
  # include Mongoid::Document
  # field :name, type: String
  # field :attachment, type: String
  mount_uploader :attachment, AttachmentUploader # Tells rails to use this uploader for this model.
  validates :name, presence: true # Make sure the owner's name is present.
end
