# Fabric TODOs

An implementation of the classical TODO MVC application using ClojureScript, [Reagent](http://reagent-project.github.io/), React and [Microsoft UI Fabric React](https://developer.microsoft.com/en-us/fabric#/get-started).

Based on the UI Fabric component library demo from the Microsoft [Frontend Bootcamp](https://microsoft.github.io/frontend-bootcamp/step2-02/demo/).

### Requirements

* [Java](https://adoptopenjdk.net/)
* [Leiningen](https://leiningen.org/)
* [NodeJS](https://nodejs.org/)

### Screenshot

![fabric-todos](https://user-images.githubusercontent.com/208068/58454194-825aff80-80d2-11e9-857a-1f2aedeb0898.png)

### Development mode

Navigate to the project folder and run the following commands in the terminal.

To download both the Clojure and NodeJS dependencies run:

```
lein deps
```

To start the Figwheel compiler run the following command:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.
Once Figwheel starts up, you should be able to open the `public/index.html` page in the browser.

### REPL

The project is setup to start nREPL on port `7002` once Figwheel starts.
Once you connect to the nREPL, run `(cljs)` to switch to the ClojureScript REPL.

### Building for production

```
lein clean
lein package
```
