@Test
void testAssertions() {
    // = (equals)
    assertEquals(5, 2 + 3);
    assertEquals("Hello", "Hel" + "lo");

    // So sánh object
    Task expected = new Task("Title", "Desc");
    Task actual = new Task("Title", "Desc");
    assertEquals(expected, actual);  // Dùng equals()

    // Null checks
    assertNull(getNullValue());
    assertNotNull(getNonNullValue());

    // Boolean checks
    assertTrue(isValid());
    assertFalse(isInvalid());

    // Exception
    assertThrows(IllegalArgumentException.class, () -> {
        throw new IllegalArgumentException("error");
    });

    // Multiple assertions (tất cả chạy dù có lỗi)
    assertAll("Task properties",
        () -> assertEquals("Title", task.getTitle()),
        () -> assertEquals("Desc", task.getDescription()),
        () -> assertFalse(task.isCompleted())
    );
}
