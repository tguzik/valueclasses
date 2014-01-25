# commons-utils

 **Please note that this is still work in progress **

Collection of annotations and utilities that make writing Java applications more readable. Truth be told, none of these are 
ground breaking in any sense, but attempt to enable developers to write cleaner code that is easier to read and use, 
letting them do the real work, instead of wondering "what happens if I give this function that?".


## Communication is hard, let's go shopping


It's no secret that communication is a key to successful projects, opensource or properiary. The thing is,
us technical folks often need to know the gory details that are not always available in the documentation.


### Annotations? What's wrong with TODO, FIXME and Javadoc?

Absolutely nothing. If that's enough for you, then go ahead and use them. The problem begins when you only have binary 
distribution of a library and you have to guess some of its properties, like failure mode (does it fail fast?), 
mutability (can I safely give it to another threads?) or whether the function can return `null` value.


## How do I get it?

At this point the sources are available only on this github page. I would like to make this library available 
through Maven at some point in future.


## So what's inside?

Please see [this|DOCS.md] file for complete listing and short descriptions of the provided classes.


## License

Source code in this repository is available under [MIT License|LICENSE]. 
