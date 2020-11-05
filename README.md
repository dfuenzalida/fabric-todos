# Fabric TODOs

An implementation of the classical TODO MVC application using ClojureScript, [Reagent](http://reagent-project.github.io/), React and [Microsoft Fluent UI](https://fluent-ui.com).

Based on the UI Fabric component library demo from the Microsoft [Frontend Bootcamp](https://microsoft.github.io/frontend-bootcamp/step2-02/demo/).

### Requirements

* [Java](https://adoptopenjdk.net/)
* [NodeJS](https://nodejs.org/)
* [Shadow-cljs](https://shadow-cljs.org/)
* [Yarn](https://yarnpkg.com/)

### Screenshot

![fabric-todos](https://user-images.githubusercontent.com/208068/58454194-825aff80-80d2-11e9-857a-1f2aedeb0898.png)

### Development mode

Navigate to the project folder and run the following commands in the terminal.

To download the NodeJS dependencies run:

```
yarn install
```

Copy the static HTML file to the target folder with:

```
yarn html
```

To start the compiler in watch mode:

```
yarn watch
```

Shadow-cljs will automatically push cljs changes to the browser.
Once the ClojureScript code is compiled, visit http://localhost:8080/

### REPL

On watch mode a nREPL will be started on port 37117:

```
$ yarn watch
yarn run v1.22.10
$ shadow-cljs watch app
shadow-cljs - config: /home/denis/Projects/ClojureScript/fabric-todos/shadow-cljs.edn
shadow-cljs - HTTP server available at http://localhost:8080
shadow-cljs - server version: 2.11.7 running at http://localhost:9630
shadow-cljs - nREPL server started on port 37117
shadow-cljs - watching build :app
```

Once you connect to the nREPL, you can run functions from the REPL, like the following bit to create a new TODO:

```
(ns fabric-todos.state) ;; Change to the namespace where 'add-todo' is defined

(add-todo "This task was created from the REPL")
```

You should see the new task created at the end of the list.


### License

MIT


