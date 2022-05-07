# DocMan
![themeImg](https://raw.githubusercontent.com/jyu42/DocMan/main/assets/themeImg.jpeg)
## Document Management System

This project aims at providing a GUI software to allow user to write their documentation for their project more conveniently.

## Dependency

* openjdk 11.0.13
* openjfx 11.0.2

# Features
* Live preview
* `SUPER` rich text (fully customize text style & media insert)
* Pre-define structure
* Maximum of two layer table structure
* Draggabel & resizeable GUI
* Open & save source file
* Convert to HTML
* Print support (allow user to print the document to paper or PDF file)
  
![app](https://raw.githubusercontent.com/jyu42/DocMan/main/assets/app.png)

## Run
### Use VSCode
Navigate to the root of the repo and enter *code .* to open directory with VSCode.
*ctrl+shift+B* to compile and run the code.

### Use command line
Navigate to the root of the repo and type:
```
# Compile
mvn compile

# Run
mvn exec:java  -Dexec.mainClass="DocMan"
```
Alternatively, you can directly run the following command:
```
# One line command
mvn compile exec:java  -Dexec.mainClass="DocMan"
```
or
```
./run.sh 
```

## Usage
### Define Document Structure


```
StructBegin
Title{PrettyTitle}
Author{Your Pretty Name}

Section{Intro}
Section{Usage}

...
StructEnd

/Section{Intro}
This is a very useful project.
/SectionEnd

/Section{Usage}
This is how you're gonna use it.

/SubsecStart{Get Ready}
Isn't great?
/SubsecEnd

/SectionEnd

...
```

You can insert an image with url:

```
<img src="https://raw.githubusercontent.com/jyu42/DocMan/main/assets/themeImg.jpeg" alt="Theme Image" width="200" >
```

If you want to change the text style:

```
The process of installation is <span style="font-weight: bold;color:blue">easy</span>.
```

In summary, you can do *whatever you want* to enrich the document as long as you follow the rules of `HTML` and `DocMan` format.