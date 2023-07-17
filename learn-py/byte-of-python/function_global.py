#global 语句：给一个在程序顶层的变量赋值

x = 50

def func():
    global x
    print('x is', x)
    x = 2
    print('Changed global x to', x)


func()
print('Value of x is', x)