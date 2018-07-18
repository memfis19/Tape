# Tape
[ ![Download](https://api.bintray.com/packages/m-e-m-f-i-s/io.github.memfis19/tape/images/download.svg) ](https://bintray.com/m-e-m-f-i-s/io.github.memfis19/tape/_latestVersion)

Plugin for android app to modify app icon. Useful for developing purposes i.e. to show deployment kind, build type or version. Supports raster images usually png-s and vector images (using <a href='https://xmlgraphics.apache.org/batik/'>Apache Batik</a>).

## How to add?
1. Add buildscript dependency: ```classpath 'me.surzhenko.rodion:tape:1.0.0'```
2. Apply plugin: ```apply plugin: 'icon-tape'```

## How to use?
To start using you should add next extension section to your app ```build.gradle``` script, i.e.:
```
tape {
    stripeColor = 0xCC991155
    textColor = 0xFFDDAA11
    fontSize = 36
    text = ["Test", "Dev"]
    buildTypes = ["debug"]
}
```
Please notice that color supports transparency so please pass alpha in color values too. 

### Sample
<img src="https://github.com/memfis19/Tape/blob/master/test/ic_launcher.png"/> <img src="https://github.com/memfis19/Tape/blob/master/raster.png"/>

## Bugs and Feedback
For bugs, feature requests, and discussion please use <a href="https://github.com/memfis19/Tape/issues">GitHub Issues</a>.

# [LICENSE](/LICENSE.md)

###### MIT License

###### Copyright (c) 2018 Rodion Surzhenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
