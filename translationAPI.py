#Doing some tests to see how well this works.
#Please install googletrans before trying this. 


from googletrans import Translator
translator = Translator()
spResult = translator.translate('como estas')
frResult = translator.translate('Le codage est cool')
grResult = translator.translate('du bist cool')

print spResult
print frResult
print grResult


