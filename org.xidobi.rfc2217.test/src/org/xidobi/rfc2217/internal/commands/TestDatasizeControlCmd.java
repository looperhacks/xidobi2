/*
 * Copyright Gemtec GmbH 2009-2013
 *
 * Erstellt am: 22.08.2013 13:19:26
 * Erstellt von: Peter-Ren� Jeschke
 */
package org.xidobi.rfc2217.internal.commands;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.xidobi.DataBits;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.xidobi.DataBits.DATABITS_5;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;
import static testtools.MessageBuilder.buffer;

/**
 * Tests the class {@link DatasizeControlCmd}.
 * 
 * @author Peter-Ren� Jeschke
 */
public class TestDatasizeControlCmd {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private DatasizeControlCmd cmd;

	@Mock
	private DataInput input;

	@Before
	public void setUp() throws IOException {
		initMocks(this);
	}

	/**
	 * When a negative datasize is supplied to the constructor, an {@link IllegalArgumentException}
	 * should be thrown.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void new_null() {
		new DatasizeControlCmd((DataBits)null);
	}

	/**
	 * Checks whether the dataSize is read correctly.
	 */
	@Test
	public void read_isCorrect() throws IOException {
		cmd = new DatasizeControlCmd(buffer(5).toDataInput());

		assertThat(cmd.getDataSize(), is(DATABITS_5));
	}

	/**
	 * When the dataSize is invalid, an {@link IOException} should be thrown.
	 */
	@Test
	public void read_invalidDataSize() throws IOException {
		exception.expect(IOException.class);
		exception.expectMessage("Unexpected data bits value: -3");


		cmd = new DatasizeControlCmd(buffer(-3).toDataInput());
	}

	/**
	 * Checks whether the encoded message is correct.
	 */
	@Test
	public void write_correctData() throws IOException {
		DataOutput output = mock(DataOutput.class);
		new DatasizeControlCmd(DATABITS_5).write(output);

		verify(output).writeByte(5); // The datasize
	}

}