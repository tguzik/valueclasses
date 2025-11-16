Changelog
=========

Below is the summary of changes in each version of the library. Changes that do
not affect the binary version (documentation) are generally not included here.
Changes that might affect the binary version (changes to sources or pom.xml) are
*always* included here.

Some items are "*Breaking*" backward compatibility: they require client code
change and/or change the expected functionality.

-----

Version `1.0.0`:

* *Breaking* Renamed `AbstractStringValueAdapter` to `JaxbStringValueAdapter`
* *Breaking* Renamed `AbstractJaxbValueAdapter` to `JaxbValueAdapter`
* Class `BaseObject` now has a convenience static method
  `toString(@Nullable Object, @Nonnull ToStringStyle)` that, as you might have
  guessed, does reflection toString on given object using given style.
* Class `MultilineNoAddressStyle` is now a public class with public constructor.
* Switched from `Charset.forName(String)` to `StandardCharsets` in `Loader`.
* Bumped dependency on `com.tguzik:annotations` to `1.0.0`.
* Added static code analysis and mutation testing to the `verify` build phase

Version `0.6`:

* Pinned dependencies to specific versions. This should make this library less
  annoying to use when you have access to Maven Central Releases repository,
  but not to the Snapshots repository. This occurs fairly often if you use a
  local mirror of Maven Central.
* `Loader` and `Normalize` were changed from final classes to enums without
  values. Instances of both classes were meaningless as these had only static
  methods.
* Next release will jump to version 1.0 and start using semantic versioning.

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
