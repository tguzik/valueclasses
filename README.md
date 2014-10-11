# valueclasses [![Build Status](https://travis-ci.org/tguzik/valueclasses.png?branch=master)](https://travis-ci.org/tguzik/valueclasses)

This small library aims to provide a blueprint for [value-based classes](http://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html)
on JVM before Project Valhalla hits the wide use. These blueprints are designed to hold a single, usually primitive 
value while at the same time giving them distinct *type* information and thus leveraging Java type system. The aim is
to help you create your complex applications by spending less time on frustrating tasks fixing bugs that are the 
result of using wrong variable in function call, which is often the case in *Stringly-typed* frameworks (we've all 
seen these).

For example, let's say that you have an use case where you have to update account balance for a customer, 
while saving Point Of Sale identifier and an optional comment:

```java
public void updateAccountBalance( Long customerId, Long pointOfSaleId, Long delta, @Nullable String comment ) {
    // [...]
}
```

Now, somewhere in the middle of your business logic you do something that boils down to:

```java
public void processTransaction( TransactionContext context, Customer customer ) {
    // [...]
    updateAccountBalance( customer.getId(),
                          context.getId(),
                          context.getPriceInCents(),
                          context.getTransactionTitle() );
    // [...]
}
```

Now, this is fine and dandy, but what happens when the method to update the balance changes its signature without 
changing all invocations? How soon would you know that something isn't right? Of course, 
this should come up in unit tests, but that depends on their quality.

Instead of relying on something that *might* be there and *might* find the mistake, the idea is to enforce this at 
compilation stage. This way most of the mistakes will be weeded out by the time the tests are ran. Let's take a look 
at this method signature:

```java
public void updateAccountBalance( CustomerId customerId,
                                  PointOfSaleId pointOfSaleId,
                                  PriceInCents delta,
                                  @Nullable String comment ) {
    // [...]
}
```

...and tell me, how hard you would have to try to make something that would pass as an honest mistake? :-)

Of course, you should use these value-based objects where it is reasonable. Data model classes, complicated APIs and 
business logic are a good places for them. On the other hand tight loops in graphics processing are not.


## How do I get it?

The library is available in Maven Central repository. You can use it in your projects via this dependency:

    <dependency>
        <groupId>com.tguzik</groupId>
        <artifactId>valueclasses</artifactId>
        <version>1.0.0</version>
    </dependency>


## So what's inside?

See [Javadoc](http://tguzik.github.io/valueclasses/) or the demo code below for more information.


## Short demo

Here are some examples of different types of value-based classes you can create with this library:

```java
import com.tguzik.value.adapters.JaxbStringValueAdapter;
import com.tguzik.value.StringValue;
// [...]

@Immutable
@XmlJavaTypeAdapter( value = FirstName.Adapter.class )
public final class FirstName extends StringValue
{
    private FirstName( String value ) {
        super( value );
    }

    public static FirstName valueOf( String firstName ) {
        return new FirstName( StringUtils.trimToEmpty( firstName ) );
    }

    public static class Adapter extends JaxbStringValueAdapter<FirstName>
    {
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
public final class CustomerId extends Value<Long>
{
    private CustomerId( Long value ) {
        super( value );
    }

    public static CustomerId valueOf( Long value ) {
        /* You can plug additional validation (recommended) or an instance cache here, if you need one. */
        return new CustomerId( value );
    }

    /** For the purpose of this example let's assume you use JaxB-compatible library */
    public static class Adapter extends JaxbValueAdapter<Long, CustomerId>
    {
        @Override
        protected ClientId createNewInstance( Long value ) {
            return ClientId.valueOf( value );
        }
    }
}
```

```java
public final class LastName extends StringValue { [...] }
public final class EmailAddress extends StringValue { [...] }
```

   
Now, assuming that you did go out of your way to create these value-based objects mutable (which you shouldn't), 
you can create this immutable class to hold the data about customer:

```java
/*
 * Reflection-based .hashCode(), .equals() and .toString() are already defined in the com.tguzik.objects.BaseObject class.
 */
@Immutable
public final class Customer extends BaseObject {
    private final CustomerId customerId;
    private final FirstName firstName;
    private final LastName lastName;
    private final EmailAddress emailAddress;
    // And whatever else you need

    public Customer(CustomerId customerId, FirstName firstName, LastName lastName, EmailAddress emailAddress) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
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

## Dependencies

Outside of unit tests, this project depends on following third party libraries:

* `org.apache.commons:commons-lang3`
* `javax.xml.bind:jaxb-api`
* `com.tguzik:annotatons`
* JDK 1.7+


## License

Source code in this repository is available under [MIT License](LICENSE).
 
