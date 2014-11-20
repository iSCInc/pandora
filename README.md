# Pandora

An API proxy service for productizing the Mercury API with the
[guidelines](https://github.com/Wikia/guidelines/tree/master/APIDesign).

There is a draft design document [here](DESIGN.md).

## Code Layout

 * `pandora.api.*`: API request handlers live here.
 * `pandora.domain.*`: Pure domain logic belongs here.
 * `pandora.gateway.*`: External service client code belongs here.
 * `pandora.service.*`: Verbs that don’t belong in the domain.
 * `pandora.utils.*`: Utility functions.
 * `pandora.homeless`: Needs a home.

## Usage

At the REPL:

```clojure
; launch the server in dev
user=> (start)
...
user=> (stop)
```

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
