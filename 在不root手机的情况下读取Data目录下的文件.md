真机没root无法读取数据这件事真心麻烦。。。root之后发现我还是属于有root没权限那类人。。。
好在找到这篇。。。手舞之足蹈之，点赞之收藏之。。。

使用adb命令时的错误
如果直接使用adb命令会产生以下错误:
[plain] view plaincopy
127|shell@android:/ $ cd /data  
cd /data  
shell@android:/data $ ls  
ls  
opendir failed, Permission denied  
你是没有权限的。


正确使用adb读取data目录下的文件方式
[plain] view plaincopy
shell@android:/data $ run-as com.your.package  
run-as com.your.package  
shell@android:/data/data/com.your.package $ cd /data/data/com.your.package  
cd /data/data/com.your.package  
shell@android:/data/data/com.your.package $ ls  
ls  
cache  
databases  
lib  
shared_prefs  
shell@android:/data/data/com.your.package $ cd databases  
cd databases  
shell@android:/data/data/com.your.package/databases $ ls  
yourpackagename.db  
$ cat preferences.db > /mnt/sdcard/yourpackagename.db     
将你要访问的package目录下的db文件拷贝到sdcard中，这样就可以正常访问了！


参考资料
Read Android Data Folder Without Rooting
