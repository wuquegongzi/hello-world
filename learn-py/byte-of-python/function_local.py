#局部变量

x = 50

def func(x):
    print('x is ', x)
    x = 2
    print('Change localx to ', x)


func(x)
print('x is still', x)