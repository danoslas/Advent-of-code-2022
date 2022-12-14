import java.util.*;

final class Directory {

    private final String name;
    private Directory parent;
    private final Set<Directory> subdirectories = new HashSet<>();
    private int size = 0;

    public Directory(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    public void addSubdirectory(final Directory dir) {
        dir.parent = this;
        subdirectories.add(dir);
    }

    public void addLocalFileSize(final int size) {
        this.size += size;
        if (parent != null) {
            parent.addLocalFileSize(size);
        }
    }

    public Optional<Directory> getParent() {
        return Optional.ofNullable(parent);
    }

    public Collection<Directory> getSubdirectories() {
        return subdirectories;
    }

    public Optional<Directory> getSubdirectoryByName(final String name) {
        return subdirectories.stream()
                .filter(dir -> dir.name.equals(name))
                .findFirst();
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Directory directory = (Directory) o;

        return name.equals(directory.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
