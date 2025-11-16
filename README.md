# valueclasses

This small library aims to provide a blueprint
for [value-based classes](http://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html)
on JVM before [Project Valhalla](https://openjdk.org/projects/valhalla/) becomes widely available.

These blueprints are designed to hold a single, immutable, and usually primitive value. At the same time, instances of
these classes give them distinct *type* information to distinguish pieces of data that may have equal value but vastly
different meaning at compile time. Since this check is performed at compile time, this helps in refactoring and
maintenance of complex applications, especially when dealing with "*stringly-typed*" APIs or frameworks.

For example, let's say we have an API that updates account balance that also saves the identifier of the Point Of Sale
and an optional transaction title/comment:

```java
public void updateAccountBalance( long customerId, long pointOfSaleId, long centsDelta, @Nullable String title ) {
  // [...]
}
```

Now, let's say that another module contains transaction processing business logic that boils down to:

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

Of course, you should use these value-based objects where it is reasonable. Data model classes, handlers for complicated
APIs and business logic are a good places for them. On the other hand tight loops in graphics processing are not.

Side note: If you are here, then you will probably be interested in these open-source static analyzers:

* [error_prone](https://github.com/google/error-prone)
* [Spotbugs](https://github.com/spotbugs/spotbugs)
* [PMD](https://github.com/pmd/pmd)

All three can be used at once, since each one performs different checks. All three are integrated in this project, so it
can serve as a template for integrating them in your application.

## How do I get it?

The library is available in Maven Central repository. You can use it in your projects via this dependency:

```xml

<dependency>
  <groupId>com.tguzik</groupId>
  <artifactId>valueclasses</artifactId>
  <version>${current_version}</version>
</dependency>
```

## How do I use it?

Here are some examples of different types of value-based classes you can create with this library:

```java
import com.tguzik.value.adapters.JaxbStringValueAdapter;
import com.tguzik.value.StringValue;
// [...]

@Immutable
@XmlJavaTypeAdapter( value = FirstName.Adapter.class )
public final class FirstName extends StringValue {
  private FirstName( String value ) {
    super( value );
  }

  public static FirstName valueOf( String firstName ) {
    return new FirstName( StringUtils.trimToEmpty( firstName ) );
  }

  public static class Adapter extends JaxbStringValueAdapter<FirstName> {
    @Override
    protected FirstName createNewInstance( String value ) {
      return FirstName.valueOf( value );
    }
  }
}
```

```java
import com.tguzik.value.adapters.JaxbValueAdapter;
import com.tguzik.value.Value;
// [...]

@Immutable
@XmlJavaTypeAdapter( value = CustomerId.Adapter.class )
public final class CustomerId extends Value<Long> {
  private CustomerId( Long value ) {
    super( value );
  }

  public static CustomerId valueOf( Long value ) {
    /* You can plug additional validation (recommended) or an instance cache here, if you need one. */
    return new CustomerId( value );
  }

  /** For the purpose of this example let's assume you use JaxB-compatible library */
  public static class Adapter extends JaxbValueAdapter<Long, CustomerId> {
    @Override
    protected ClientId createNewInstance( Long value ) {
      return ClientId.valueOf( value );
    }
  }
}
```

```java
public final class LastName extends StringValue { [...]
}

public final class EmailAddress extends StringValue { [...]
}
```

Now, assuming that you didn't go out of your way to create these value-based objects mutable (which you shouldn't),
you can create this class to hold the data about customer:

```java
/*
 * Reflection-based .hashCode(), .equals() and .toString() are already defined in
 * the com.tguzik.objects.BaseObject class.
 */
@Immutable
@ParametersAreNonnullByDefault
public final class Customer extends BaseObject {
  private final CustomerId customerId;
  private final FirstName firstName;
  private final LastName lastName;
  private final EmailAddress emailAddress;
  // ..and whatever else you need

  public Customer( CustomerId customerId, FirstName firstName, LastName lastName, EmailAddress emailAddress ) {
    this.customerId = Objects.requireNonNull( customerId );
    this.firstName = Objects.requireNonNull( firstName );
    this.lastName = Objects.requireNonNull( lastName );
    this.emailAddress = Objects.requireNonNull( emailAddress );
  }

  public CustomerId getCustomerId() {
    return customerId;
  }

  public FirstName getFirstName() {
    return firstName;
  }

  public LastName getLastName() {
    return lastName;
  }

  public EmailAddress getEmailAddress() {
    return emailAddress;
  }
}
```

## Requirements

Consumption of this library:

* JDK 17+

Development of this library:

* [DevEnv](https://devenv.sh/) - reproducible development environments based on Nix.
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - this project contains code formatter settings centered around
  IntelliJ - the Community edition is more than enough.

## License

Source code in this repository is available under [MIT License](LICENSE).
