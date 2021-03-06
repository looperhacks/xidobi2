/*
 * Copyright 2013 Gemtec GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xidobi.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.xidobi.WinApi.FORMAT_MESSAGE_FROM_SYSTEM;
import static org.xidobi.WinApi.FORMAT_MESSAGE_IGNORE_INSERTS;

import java.io.IOException;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xidobi.WinApi;
import org.xidobi.spi.NativeCodeException;

/**
 * Tests class {@link Throwables}.
 * 
 * @author Tobias Bre�ler
 */
public class TestThrowables {

	private static int FORMAT = FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS;
	private static String MESSAGE = "An error message!";
	private static int ERROR_CODE = 1;

	@Mock
	private WinApi os;

	@Before
	@SuppressWarnings("javadoc")
	public void setUp() {
		initMocks(this);
	}

	/**
	 * Verifies that an {@link IllegalArgumentException} is thrown, when <code>null</code> is
	 * passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void newNativeCodeException_withNullWinApi() {
		Throwables.newNativeCodeException(null, MESSAGE, ERROR_CODE);
	}

	/**
	 * Verifies that an {@link IllegalArgumentException} is thrown, when <code>null</code> is
	 * passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void newNativeCodeException_withNullMessage() {
		Throwables.newNativeCodeException(os, null, ERROR_CODE);
	}

	/**
	 * Verifies that {@link Throwables#newNativeCodeException(WinApi, String, int)} returns a
	 * {@link NativeCodeException} with a default text, when no error message is available for the
	 * given error code.
	 */
	@Test
	public void newNativeCodeException_noNativeErrorMessage() {
		//@formatter:off
		when(os.FormatMessageA(eq(FORMAT), eq((Void) null), eq(ERROR_CODE), anyInt(), any(byte[].class), eq(255), eq((Void) null)))
			.thenReturn(0);
		//@formatter:on

		NativeCodeException result = Throwables.newNativeCodeException(os, MESSAGE, ERROR_CODE);

		assertThat(result, is(nativeCodeException("An error message!\r\nError-Code 1: No error description available")));
	}

	/**
	 * Verifies that {@link Throwables#newNativeCodeException(WinApi, String, int)} returns a
	 * {@link NativeCodeException} with an error message for the given error code.
	 */
	@Test
	public void newNativeCodeException_withNativeErrorMessage() {
		//@formatter:off
		doAnswer(withNativeErrorMessage("This is a native error\n\n")).
			when(os).FormatMessageA(eq(FORMAT), eq((Void) null), eq(ERROR_CODE), anyInt(), any(byte[].class), eq(255), eq((Void) null));
		//@formatter:on

		NativeCodeException result = Throwables.newNativeCodeException(os, MESSAGE, ERROR_CODE);

		assertThat(result, is(nativeCodeException("An error message!\r\nError-Code 1: This is a native error")));
	}

	/**
	 * Verifies that an {@link IllegalArgumentException} is thrown, when <code>null</code> is
	 * passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void newIOException_withNullWinApi() {
		Throwables.newIOException(null, MESSAGE, ERROR_CODE);
	}

	/**
	 * Verifies that an {@link IllegalArgumentException} is thrown, when <code>null</code> is
	 * passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void newIOException_withNullMessage() {
		Throwables.newIOException(os, null, ERROR_CODE);
	}

	/**
	 * Verifies that {@link Throwables#newNativeCodeException(WinApi, String, int)} returns a
	 * {@link IOException} with a default text, when no error message is available for the given
	 * error code.
	 */
	@Test
	public void newIOException_noNativeErrorMessage() {
		//@formatter:off
		when(os.FormatMessageA(eq(FORMAT), eq((Void) null), eq(ERROR_CODE), anyInt(), any(byte[].class), eq(255), eq((Void) null)))
			.thenReturn(0);
		//@formatter:on

		IOException result = Throwables.newIOException(os, MESSAGE, ERROR_CODE);

		assertThat(result, is(ioException("An error message!\r\nError-Code 1: No error description available")));
	}

	/**
	 * Verifies that {@link Throwables#newNativeCodeException(WinApi, String, int)} returns a
	 * {@link NativeCodeException} with an error message for the given error code.
	 */
	@Test
	public void newIOException_withNativeErrorMessage() {
		//@formatter:off
		doAnswer(withNativeErrorMessage("This is a native error\n\n")).
			when(os).FormatMessageA(eq(FORMAT), eq((Void) null), eq(ERROR_CODE), anyInt(), any(byte[].class), eq(255), eq((Void) null));
		//@formatter:on

		IOException result = Throwables.newIOException(os, MESSAGE, ERROR_CODE);

		assertThat(result, is(ioException("An error message!\r\nError-Code 1: This is a native error")));
	}

	/**
	 * Verifies that an {@link IllegalArgumentException} is thrown, when <code>null</code> is
	 * passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getErrorMessage_withNullWinApi() {
		Throwables.getErrorMessage(os, null, ERROR_CODE);
	}

	/**
	 * Verifies that an {@link IllegalArgumentException} is thrown, when <code>null</code> is
	 * passed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getErrorMessage_withNullMessage() {
		Throwables.getErrorMessage(os, null, ERROR_CODE);
	}

	/**
	 * Verifies that {@link Throwables#newNativeCodeException(WinApi, String, int)} returns a
	 * {@link String} with a default error description, when no error message is available for the
	 * given error code.
	 */
	@Test
	public void getErrorMessage_noNativeErrorMessage() {
		//@formatter:off
		when(os.FormatMessageA(eq(FORMAT), eq((Void) null), eq(ERROR_CODE), anyInt(), any(byte[].class), eq(255), eq((Void) null)))
			.thenReturn(0);
		//@formatter:on

		String result = Throwables.getErrorMessage(os, MESSAGE, ERROR_CODE);

		assertThat(result, is(("An error message!\r\nError-Code 1: No error description available.")));
	}

	/**
	 * Verifies that {@link Throwables#newNativeCodeException(WinApi, String, int)} returns a
	 * {@link String} with an error description for the given error code.
	 */
	@Test
	public void getErrorMessage_withNativeErrorMessage() {
		//@formatter:off
		doAnswer(withNativeErrorMessage("This is a native error\n\n")).
			when(os).FormatMessageA(eq(FORMAT), eq((Void) null), eq(ERROR_CODE), anyInt(), any(byte[].class), eq(255), eq((Void) null));
		//@formatter:on

		String result = Throwables.getErrorMessage(os, MESSAGE, ERROR_CODE);

		assertThat(result, is("An error message!\r\nError-Code 1: This is a native error"));
	}

	// Utilities for this Testclass ///////////////////////////////////////////////////////////

	/** Matcher for {@link NativeCodeException} starting with the message. */
	private TypeSafeMatcher<NativeCodeException> nativeCodeException(final String message) {
		return new CustomTypeSafeMatcher<NativeCodeException>("NativeCodeException with message >" + message + "<") {
			@Override
			protected boolean matchesSafely(NativeCodeException actual) {
				return (actual.getMessage().startsWith(message));
			}
		};
	}

	/** Matcher for {@link IOException} starting with the message. */
	private TypeSafeMatcher<IOException> ioException(final String message) {
		return new CustomTypeSafeMatcher<IOException>("IOException with message >" + message + "<") {
			@Override
			protected boolean matchesSafely(IOException actual) {
				return (actual.getMessage().startsWith(message));
			}
		};
	}

	/** {@link Answer} for FormatMessageA() that returns a native error message. */
	private Answer<Integer> withNativeErrorMessage(final String nativeErrorMessage) {
		return new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				byte[] lpBuffer = (byte[]) invocation.getArguments()[4];
				copyToBytes(nativeErrorMessage, lpBuffer);
				return nativeErrorMessage.length();
			}
		};
	}

	/**
	 * Copies the bytes from the given {@link String} to the byte array.
	 */
	private void copyToBytes(final String source, byte[] destination) {
		for (int i = 0; i < source.length(); i++)
			destination[i] = source.getBytes()[i];
	}

}
