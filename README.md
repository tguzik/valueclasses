# valueclasses

This library aims to provide blueprints
for [value-based classes](http://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html)
on JVM before [Project Valhalla](https://openjdk.org/projects/valhalla/) becomes widely available.

These blueprints help in creation of objects that hold a single, immutable, and usually a primitive value. At the
same time, instances of these objects give them distinct compile-time *type* information to distinguish pieces of
data that may have equal value but vastly different meaning. Since this check is performed at as early as possible
in the development process, it saves time during refactoring and maintenance of complex applications, especially
when dealing with "*stringly-typed*" APIs or frameworks.

For example, let's say we have an API that updates account balance during a transaction:

```java
public void updateAccountBalance( long customerId, long pointOfSaleId, long centsDelta, @Nullable String title ) {
  // [...]
}
```

Now, let's say that another module contains business logic that boils down to:

```java
public void processTransaction( TransactionContext context, Customer customer ) {
  // [...]
  updateAccountBalance( customer.getId(), context.getId(), context.getPriceInCents(), context.getTransactionTitle() );
  // [...]
}
```

While this is usually okay, if the order of arguments was swapped during refactoring, a silent merge conflict, or an IDE
hiccup, the compiler would still accept the code and the issue would be found later in the development process (assuming
tests are up to standard).

Instead of relying on something that *might* be there and *might* find the mistake, the idea is to enforce correctness
at compilation stage.

If the function from this example would use value-based classes in its signature, like in an example below, similar
mistake would be immediately highlighted by the compiler with an error that the types do not match:

```java
public void updateAccountBalance( CustomerId customerId,
                                  PointOfSaleId pointOfSaleId,
                                  PriceInCents delta,
                                  @Nullable String comment ) {
  // [...]
}
```

Since these value-based classes are regular objects, you can also define methods that convert the data into
different forms.

As with any project, these value-based objects should only be used where it is reasonable. Data model classes, handlers
for complicated APIs and business logic are a good places for them. On the other hand tight loops in graphics
processing are not.

Side note: If you are here, then you will probably be interested in these open-source static analyzers
[error_prone](https://github.com/google/error-prone), [Spotbugs](https://github.com/spotbugs/spotbugs) and
[PMD](https://github.com/pmd/pmd). All three can be used at once, as each one performs different checks. All three
are integrated in this project, so it can serve as an example for integrating them in your application.

## How do I get it?

The library is available in Maven Central repository. You can use it in your projects via this dependency:

```xml

<dependencies>
  <dependency>
    <!-- Recommended: -->
    <groupId>com.tguzik</groupId>
    <artifactId>valueclasses</artifactId>
    <version>${current_version}</version>
  </dependency>

  <dependency>
    <!-- Module preserving non-core, auxiliary implementations of classes included with the 1.x release: -->
    <groupId>com.tguzik</groupId>
    <artifactId>valueclasses-legacy</artifactId>
    <version>${current_version}</version>
  </dependency>
</dependencies>
```

## How do I use it?

Starting with version 2.x of this library and JDK17 the preference is to create valueclasses backed by Java Records:

```java
import com.tguzik.traits.HasValue;
import org.jspecify.annotations.NullMarked;

@NullMarked
record CustomerId(long value) implements HasValue<Long> {
  @Override
  public Long get() {
    return value;
  }
}
```

The library offers a specialized interface that brings in several convenience functions for Strings (`length()`,
`isEmpty()`, `isBlank()`), which can be used like so:

```java
import java.util.Objects;

import com.tguzik.traits.HasStringValue;
import org.jspecify.annotations.NullMarked;

@NullMarked
record ProductName(String value) implements HasStringValue {
  ProductName {
    Objects.requireNonNull( value );
  }

  @Override
  public String get() {
    return value;
  }
}
```

The instances can be easily integrated with Jackson:

```java
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
record AirportCode(String value) implements HasStringValue {

  @JsonCreator
  AirportCode( @Nullable final String value ) {
    // Optional normalization/transformation:
    this.value = StringUtils.trimToEmpty( value ).toUpperCase( Locale.ROOT );
  }

  @Override
  @JsonValue
  public String get() {
    return value;
  }
}
```

(Note that in the above example the `@JsonCreator` annotation is optional - it has been added for clarity.)

The interface can be also applied on Enums, with this example also including integration with Jackson:

```java
import java.util.Locale;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public enum SampleEnum implements HasStringValue {
  FIRST( "ABC" ),
  SECOND( "BCD" ),
  THIRD( "DEF" );

  private final String value;

  SampleEnum( final String value ) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String get() {
    return value;
  }

  public Optional<SampleEnum> fromString( @Nullable final String input ) {
    final String trimmed = StringUtils.trimToEmpty( input ).toUpperCase( Locale.ROOT );

    for ( final SampleEnum entry : values() ) {
      if ( entry.value.equals( trimmed ) ) {
        return Optional.of( entry );
      }
    }

    return Optional.empty();
  }

  @Nullable
  @JsonCreator
  public SampleEnum jacksonForValue( @Nullable final String input ) {
    return fromString( input ).orElse( null );
  }
}
```

<details>
<summary>HACK: Make the record-based declaration even more concise</summary>

The record-based valueclass declaration can be made even more concise, and can enable integration with Jackson by
default, by declaring an interface similar to this one:

```java
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.traits.HasStringValue;

public interface TerseStringValue extends HasStringValue {
  @Override
  @JsonValue
  default String get() {
    return value();
  }

  String value();
}
```

Then the valueclass declaration would look like this:

```java
import java.util.Objects;

import org.jspecify.annotations.NullMarked;

@NullMarked
record TerseRecordStringValue(String value) implements TerseStringValue {
  TerseRecordStringValue {
    Objects.requireNonNull( value );
  }
}
```

Note that this interface declaration pretty much locks implementations into naming the field `value`. This may be fine
for 95% of applications, however it is still a design decision that needs to be made by application using this
library, and so it is not supported out of the box.

</details>

Then, these valueclasses can be used in application code, with this example showing what could be a simple DTO or a
domain model:

```java
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@JsonRootName( "Customer" )
public record Customer(@JsonProperty( "CustomerId" ) long id,
                       @JsonProperty( "Name" ) CustomerName name,
                       @JsonProperty( "Email" ) EmailAddress email,
                       @Nullable @JsonProperty( "CreationDate" ) LocalDateTime creationDate,
                       @Nullable @JsonProperty( "LastModificationDate" ) LocalDateTime lastModificationDate) {

  public Customer {
    Objects.requireNonNull( name );
    Objects.requireNonNull( email );
  }
}
```

Even through that record-based valueclasses and DTOs are preferred, the library also offers a `BaseObject` *class* that
includes reflection-based implementations of `equals()`, `hashCode()` and `toString()`:

<details>
<summary>Code snippet with an example usage of `BaseObject`</summary>

```java
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tguzik.objects.BaseObject;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/*
 * Reflection-based .hashCode(), .equals() and .toString() are already defined in
 * the com.tguzik.objects.BaseObject class.
 */
@NullMarked
@JsonRootName( "Customer" )
public final class Customer extends BaseObject {
  @JsonProperty( "CustomerId" )
  private final long id;

  @JsonProperty( "Name" )
  private final CustomerName name;

  @JsonProperty( "Email" )
  private final EmailAddress email;

  @Nullable
  @JsonProperty( "CreationDate" )
  private final LocalDateTime creationDate;

  @Nullable
  @JsonProperty( "LastModificationDate" )
  private final LocalDateTime lastModificationDate;

  public Customer( final long id,
                   final CustomerName name,
                   final EmailAddress email,
                   @Nullable final LocalDateTime creationDate,
                   @Nullable final LocalDateTime lastModificationDate ) {
    this.id = id;
    this.name = Objects.requireNonNull( name );
    this.email = Objects.requireNonNull( email );
    this.creationDate = creationDate;
    this.lastModificationDate = lastModificationDate;
  }

  public long getId() {
    return id;
  }

  public CustomerName getName() {
    return name;
  }

  public EmailAddress getEmail() {
    return email;
  }

  @Nullable
  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  @Nullable
  public LocalDateTime getLastModificationDate() {
    return lastModificationDate;
  }
}
```

</details>

Users of the 1.x version of this library can still use *classes* `c.t.v.Value` and `c.t.v.StringValue`, however as
mentioned above the general preference is to use valueclasses backed by Java Records:

<details>
<summary>Code snippet with example usage of `c.t.v.Value` and `c.t.v.StringValue`</summary>

Example usage of `c.t.v.Value`:

```java
import com.tguzik.value.Value;
import org.jspecify.annotations.NullMarked;

@NullMarked
class CustomerId extends Value<Long> {

  public CustomerId( final long value ) {
    super( value );
  }
}
```

Declaration that enables integration with Jackson:

```java
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.Value;
import org.jspecify.annotations.NullMarked;

@NullMarked
class CustomerId extends Value<Long> {

  @JsonCreator
  public CustomerId( final long value ) {
    super( value );
  }

  @Override
  @JsonValue
  public Long get() {
    return super.get();
  }
}
```

Example usage of `c.t.v.StringValue` that already includes integration with Jackson:

```java
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tguzik.value.StringValue;
import org.jspecify.annotations.NullMarked;

@NullMarked
class ProductName extends StringValue {

  @JsonCreator
  public ProductName( final String value ) {
    super( Objects.requireNonNull( value ) );
  }

  @Override
  @JsonValue
  public String get() {
    return super.get();
  }
}
```

</details>

## Requirements

Consumption of this library:

* JDK 17+

Development of this library:

* [DevEnv](https://devenv.sh/) - reproducible development environments based on Nix.
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - this project contains code formatter settings centered around
  IntelliJ - the Community edition is more than enough.

## License

Source code in this repository is available under [MIT License](LICENSE).
