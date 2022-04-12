# DocMan
### Document Management System

This project aims at providing a GUI software to allow user to write their documentation for their project more conveniently.

## Dependency

* openjdk 11.0.13
* openjfx 11.0.2

## Usage
### Use VSCode
Navigate to the root of the repo and enter *code .* to open directory with VSCode.
*ctrl+shift+B* to compile and run the code.

### Use command line
Navigate to the root of the repo and type:
```
javac DocMan.java \
--module-path /usr/share/openjfx/lib \
--add-modules javafx.graphics,javafx.web,javafx.fxml && \
java --module-path /usr/share/openjfx/lib \
--add-modules javafx.graphics,javafx.web DocMan

```