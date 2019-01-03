
    (function(){

                //自己的命名空间，外部无法访问。
                //定义了自己的函数$()
                Function hi()
                {
                    //代码。
                    //测试是否成功。
                    alert("你好！");
                }
                //构造自己的命名空间。
                Window['note']={}
                //将自己的命名空间注册到window，并且注册自己的$函数。
                Window['note']['hi']=hi;
       })();