// ============ WHEN/THEN - Cấu hình mock ============
when(mock.method()).thenReturn(value);          // Trả về giá trị
when(mock.method()).thenThrow(exception);        // Ném exception
when(mock.method(anyString())).thenReturn(v);    // Với any parameter

// ============ VERIFY - Kiểm tra mock được gọi ============
verify(mock, times(1)).method();       // Được gọi 1 lần
verify(mock, never()).method();        // Không bao giờ được gọi
verify(mock, atLeastOnce()).method();  // Ít nhất 1 lần
verify(mock).method(argThat(x -> x > 5));  // Với điều kiện

// ============ Argument Matchers ============
any()           // Any object
anyString()     // Any String
anyLong()       // Any Long
eq(value)       // Exact value
anyInt()
