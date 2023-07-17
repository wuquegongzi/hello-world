#控制流
number = 23
guess = int(input('Enter an Integer : '))

if guess == number:
    print('Congratulations, you guessed it.')
    print('(but you do not win any prizes!)')
elif  guess < number:
    print('No, it is a little higher than that')
    # todo
else:
    print('No, it is a little lower than that')

print('Done')

if True:
    print("Yes,it is true")