# Be sure to restart your server when you modify this file.

# Your secret key is used for verifying the integrity of signed cookies.
# If you change this key, all old signed cookies will become invalid!

# Make sure the secret is at least 30 characters and all random,
# no regular words or you'll be exposed to dictionary attacks.
# You can use `rake secret` to generate a secure secret key.

# Make sure your secret_key_base is kept private
# if you're sharing your code publicly.
#API::Application.config.secret_key_base = ENV['SECRET_KEY_BASE_TOKEN']
API::Application.config.secret_key_base = '2c28d105797129e8668ecb66e08bd5aab3a5fee8a694707002f1b5a1e73e1fa9ddbae90f44b45db642fa250f7bcdd7089613b44cc4f97c8babdaea62eaa1fb18'
