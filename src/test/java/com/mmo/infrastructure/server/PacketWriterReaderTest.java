package com.mmo.infrastructure.server;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class PacketWriterReaderTest {

    @Test
    public void writeAndRead() throws IOException {
        int expectedValue1 = 37;
        boolean expectedValue2 = true;
        char expectedValue3 = 'c';
        double expectedValue4 = 0.3;
        float expectedValue5 = 0.9F;
        int expectedValue6 = 212312;
        long expectedValue7 = 1232131;
        short expectedValue8 = 88;
        String expectedValue9 = "ûTf stríng";

        int resultValue1;
        boolean resultValue2;
        char resultValue3;
        double resultValue4;
        float resultValue5;
        int resultValue6;
        long resultValue7;
        short resultValue8;
        String resultValue9;

        byte[] bytes;

        try (PacketWriter writer = new PacketWriter()) {
            writer.write(expectedValue1);
            writer.writeBoolean(expectedValue2);
            writer.writeChar(expectedValue3);
            writer.writeDouble(expectedValue4);
            writer.writeFloat(expectedValue5);
            writer.writeInt(expectedValue6);
            writer.writeLong(expectedValue7);
            writer.writeShort(expectedValue8);
            writer.writeUTF(expectedValue9);

            bytes = writer.toBytes();
        }

        try (PacketReader reader = new PacketReader(bytes)) {
            resultValue1 = reader.read();
            resultValue2 = reader.readBoolean();
            resultValue3 = reader.readChar();
            resultValue4 = reader.readDouble();
            resultValue5 = reader.readFloat();
            resultValue6 = reader.readInt();
            resultValue7 = reader.readLong();
            resultValue8 = reader.readShort();
            resultValue9 = reader.readUTF();
        }

        assertThat(resultValue1, equalTo(expectedValue1));
        assertThat(resultValue2, equalTo(expectedValue2));
        assertThat(resultValue3, equalTo(expectedValue3));
        assertThat(resultValue4, equalTo(expectedValue4));
        assertThat(resultValue5, equalTo(expectedValue5));
        assertThat(resultValue6, equalTo(expectedValue6));
        assertThat(resultValue7, equalTo(expectedValue7));
        assertThat(resultValue8, equalTo(expectedValue8));
        assertThat(resultValue9, equalTo(expectedValue9));
    }
}
