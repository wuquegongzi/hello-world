while True:
    s = input('Enter something : ')
    if s =='quit':
        break
    if len(s) <= 3:
        print('Too small')
        continue
    print('Length of the string is ',len(s))
print('Done')