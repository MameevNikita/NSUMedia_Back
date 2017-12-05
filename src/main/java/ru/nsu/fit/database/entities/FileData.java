package ru.nsu.fit.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "FILE_DATAS")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private File file;

    @Lob
    private byte[] data;

    public FileData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FileData(File file, byte[] data) {
        this.file = file;
        this.data = data;
    }
}
