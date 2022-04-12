# DocMan
### Document Management System

This project aims at providing a GUI software to allow user to write their documentation for their project more conveniently.

## Dependency

* openjdk 11.0.13
* openjfx 11.0.2

# Features
* Live preview
* Pre-define structure
* Maximum of two layer table structure
* Draggabel & resizeable GUI

## Run
### Use VSCode
Navigate to the root of the repo and enter *code .* to open directory with VSCode.
*ctrl+shift+B* to compile and run the code.

### Use command line
Navigate to the root of the repo and type:
```
# Compile
javac DocMan.java \
--module-path /usr/share/openjfx/lib \
--add-modules javafx.graphics,javafx.web,javafx.fxml

# Run
java --module-path /usr/share/openjfx/lib \
--add-modules javafx.graphics,javafx.web DocMan
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
