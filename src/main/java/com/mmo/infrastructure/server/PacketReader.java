package com.mmo.infrastructure.server;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.util.UUID;

public class PacketReader implements Closeable {

    private ByteArrayInputStream byteArrayInputStream;
    private DataInputStream dataInputStream;

    public PacketReader(byte[] bytes) {
        byteArrayInputStream = new ByteArrayInputStream(bytes);
        dataInputStream = new DataInputStream(byteArrayInputStream);
    }

    @Override
    public void close() {
        try {
            byteArrayInputStream.close();
            dataInputStream.close();
        } catch (Exception exception) {
            throw new PacketCloseException(exception, "Failed to close stream(s)");
        }
    }

    public int read() {
        return read(() -> dataInputStream.read());
    }

    public short readShort() {
        return read(() -> dataInputStream.readShort());
    }

    public int readInt() {
        return read(() -> dataInputStream.readInt());
    }

    public long readLong() {
        return read(() -> dataInputStream.readLong());
    }

    public float readFloat() {
        return read(() -> dataInputStream.readFloat());
    }

    public double readDouble() {
        return read(() -> dataInputStream.readDouble());
    }

    public char readChar() {
        return read(() -> dataInputStream.readChar());
    }

    public boolean readBoolean() {
        return read(() -> dataInputStream.readBoolean());
    }

    public String readUTF() {
        return read(() -> dataInputStream.readUTF());
    }

    public UUID readUUID() {
        return read(() -> new UUID(dataInputStream.readLong(), dataInputStream.readLong()));
    }

    private static <T> T read(Reader<T> reader) {
        try {
            return reader.read();
        } catch (Exception exception) {
            throw new PacketReadException(exception, "Failed to read");
        }
    }

    @FunctionalInterface
    private static interface Reader<T> {
        T read() throws Exception;
    }
}
