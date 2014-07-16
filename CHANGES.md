Changelog
=========

Below is the summary of changes in each version of the library. Changes that do
not affect the binary version (documentation) are generally not included here.
Changes that might affect the binary version (changes to sources or pom.xml) are
*always* included here.

Some items are "*Breaking*" backward compatibility: they require client code
change and/or change the expected functionality.

-----

Version `0.5`:
* *Breaking*: `BaseObject.MULTILINE_NO_ADDRESS_TOSTRING_STYLE` renamed to
  `BaseObject.MULTILINE_NO_ADDRESS_STYLE`.
* *Breaking*: Changed the style for `BaseObject.MULTILINE_NO_ADDRESS_STYLE` to
  include commas ("`,`") as a part of the field separator (it will typically
  appear at the end of each line, except for the first and the last one).
* New dependency on `com.tguzik:annotations:0.3` (`jsr305` still available as
  transitional dependency)
* Updated `pom.xml` with correct links to the license, CI and issue management
  sites.


Version `0.4`:
* Library available on Maven Central
