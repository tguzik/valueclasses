# valueclasses [![Build Status](https://travis-ci.org/tguzik/valueclasses.png?branch=master)](https://travis-ci.org/tguzik/valueclasses)

**Please note that this is still work in progress **

This small library aims to make it easier to create values with distinctive type in Java. This is useful for protecting
against null values and retaining the information about what is expected to be passed as an argument... or at least
more readable than five String arguments in a row to the same function ;-)

## How do I get it?

At this moment the library is available only through sources. Maven artifact is TBD.


## So what's inside?

See [Javadoc](http://tguzik.github.io/valueclasses/) or the demo code below for more information.


## Short demo

Suppose your project had to store multiple distinct types of values that boil down to String, Long or Integer (or anything 
else really). In this example, I'll define these types:

```java
@XmlJavaTypeAdapter( value = ClientId.JaxbAdapter.class )
public final class ClientId extends Value<Long>
{
    private ClientId( Long value ) {
        super( value );
    }

    public static ClientId valueOf( Long value ) {
        /* 
         * You could plug some cache here to reduce the number of instances created, 
         * reducing the amount of garbage your application creates.
         */
        return new ClientId( value );
    }

    public static class JaxbAdapter extends AbstractJaxbValueAdapter<Long, ClientId>
    {
        @Override
        protected ClientId createNewInstance( Long value ) {
            return ClientId.valueOf( value );
        }
    }
}
```

```java
@XmlJavaTypeAdapter( value = FirstName.JaxbAdapter.class )
public final class FirstName extends StringValue
{
    private FirstName( String value ) {
        super( value );
    }

    public static FirstName valueOf( String firstName ) {
        return new FirstName( StringUtils.trimToEmpty( firstName ) );
    }

    public static class JaxbAdapter extends AbstractStringValueAdapter<FirstName>
    {
        @Override
        protected FirstName createNewInstance( String value ) {
            return FirstName.valueOf( value );
        }
    }
}
```

```java
public final class LastName extends StringValue { [...] }
public final class EmailAddress extends StringValue { [...] }
```
    
Now, assuming that your implementations are immutable (they don't mutate state after creation), you can create following 
class to hold the data about customer:

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

Next you can adapt your API/business logic to use these defined types to make sure _nobody_ mistakenly passes first name
where email address should be:

```java
/*
 * Without this library the above method signature would probably look like:
 *
 * public void notifyOncall(String, Event);
 */
public void notifyOncall(EmailAddress address, Event badEvent) { [...] }
```

```java
/*
 * Without this library the above method signature would probably look like:
 *
 * public void modifyAccountValue(long, long);
 */
public void modifyAccountValue(CustomerId id, long delta) { [...] }
```

```java
/*
 * Without this library the above method signature would probably look like:
 *
 * public void transferAccountOwnership(long, String, String, String);
 */
public void transferAccountOwnership(CustomerId accountId,
                                     FirstName newOwnersFirstName, 
                                     LastName newOwnersLastName, 
                                     EmailAddress newOwnersEmailAddress) 
{
    [...]
}
```

## Dependencies

This project depends on following third party libraries:

* `org.apache.commons:commons-lang3`, version 3.1+
* `com.google.code.findbugs:jsr305`, version 2.0.2+
* `javax.xml.bind:jaxb-api`, version 2.2.11+
* `junit:junit`, unit tests only, version 4.11+
* `JDK1.7`


## License

Source code in this repository is available under [MIT License](LICENSE).
 