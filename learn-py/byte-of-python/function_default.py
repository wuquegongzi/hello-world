#默认参数值
# 只有那些位于参数列表末尾的参数才能被赋予默认参数值，意即在函数的参数列表中拥
# 有默认参数值的参数不能位于没有默认参数值的参数之前。 这是因为值是按参数所处的位置依次分配的。
# 举例来说， def func(a, b=5) 是有效的， 但 def func(a=5, b) 是无效的。


def say(message, times=1):
    print(message * times)

say('hello')
say('hello',5)

