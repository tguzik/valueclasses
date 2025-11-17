package com.tguzik.objects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MultilineNoAddressStyleTest {

  @Test
  void produces_expected_string_when_processing_a_regular_class() {
    final String expected = """
                            MultilineNoAddressStyleTest.FakeClass[
                              almostPI=3.14,
                              first=first string,
                              second=second string,
                              third=third string
                            ]
                            """;

    final var instance = new FakeClass();
    final String actual = BaseObject.toString( instance, new MultilineNoAddressStyle() );

    assertThat( actual ).isEqualToIgnoringNewLines( expected );
  }

  @Test
  void produces_expected_string_when_processing_a_record() {
    final String expected = """
                            MultilineNoAddressStyleTest.FakeRecord[
                              first=123,
                              second=some string,
                              third=3.14
                            ]
                            """;

    final var instance = new FakeRecord( 123, "some string", 3.14 );
    final String actual = BaseObject.toString( instance, new MultilineNoAddressStyle() );

    assertThat( actual ).isEqualToIgnoringNewLines( expected );
  }

  @SuppressWarnings( "unused" )
  static class FakeClass {
    private static final String STATIC_STRING = "this is static";
    public static String PUBLIC_STRING = "this is static";

    protected volatile String first = "first string";
    final String second = "second string";
    public String third = "third string";
    double almostPI = 3.14;
    public transient String transientField = "transient ";
  }

  @SuppressWarnings( "unused" )
  record FakeRecord(int first,
                    String second,
                    double third) {

    private static final String PRIVATE_STATIC = "private static string";
    public static String PUBLIC_STATIC = "public static string";
  }
}
