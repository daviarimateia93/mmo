package com.mmo.infrastructure.server;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketWriter implements Closeable {

    private ByteArrayOutputStream byteArrayOutputStream;
    private DataOutputStream dataOutputStream;

    public PacketWriter() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    }

    public byte[] toBytes() {
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void close() throws IOException {
        byteArrayOutputStream.close();
        dataOutputStream.close();
    }

    public void write(int value) {
        write(() -> dataOutputStream.write(value));
    }

    public void writeShort(short value) {
        write(() -> dataOutputStream.writeShort(value));
    }

    public void writeInt(int value) {
        write(() -> dataOutputStream.writeInt(value));
    }

    public void writeLong(long value) {
        write(() -> dataOutputStream.writeLong(value));
    }

    public void writeFloat(float value) {
        write(() -> dataOutputStream.writeFloat(value));
    }

    public void writeDouble(double value) {
        write(() -> dataOutputStream.writeDouble(value));
    }

    public void writeChar(char value) {
        write(() -> dataOutputStream.writeChar(value));
    }

    public void writeBoolean(boolean value) {
        write(() -> dataOutputStream.writeBoolean(value));
    }

    public void writeUTF(String value) {
        write(() -> dataOutputStream.writeUTF(value));
    }

    private static void write(Writer writer) {
        try {
            writer.write();
        } catch (Exception exception) {
            throw new PacketReadException(exception, "Failed to write");
        }
    }

    @FunctionalInterface
    private static interface Writer {
        void write() throws Exception;
    }
}
