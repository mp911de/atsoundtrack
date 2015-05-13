AtSoundtrack
==============

Plugin that provides @soundtrack based on iTunes. This plugin works only if you use a Mac.
@soundtrack can be used either with code completion or as variable in the file templates.


To setup @soundtrack in your templates: Open **Preferences** go to **Editor** -> **File and Code Templates** -> **Includes** and select "File Header"

Add the following code:

```
#if (${soundtrack} && ${soundtrack} != "")
 *  @soundtrack ${soundtrack}
#end
```

Use IntelliJ IDEA with iTunes to generate @soundtrack

![AtSoundtrack](screencast/screencast.gif)

Works also with PhpStorm, WebStorm, PyCharm and RubyMine.


License
-------

* [Apache License 2.0] (http://www.apache.org/licenses/LICENSE-2.0)

Contributing
-------

Github is for social coding: if you want to write code, I encourage contributions through pull requests from forks of this repository. 
Create Github tickets for bugs and new features and comment on the ones that you are interested in.