import org.example.customarraylist.CustomArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class CustomArrayListTest {
    private CustomArrayList<Integer> customArrayList;

    @BeforeEach
    void setUp() {
        customArrayList = new CustomArrayList<>(new Integer[]{1, 2, 3, 4});
    }

    @Test
    void testConstructorWithArray() {
        CustomArrayList<Integer> list = new CustomArrayList<>(new Integer[]{5, 6, 7});
        assertEquals(3, list.size());
        assertEquals(5, list.get(0));
        assertEquals(6, list.get(1));
        assertEquals(7, list.get(2));
    }

    @Test
    void testAddAtIndex() {
        customArrayList.add(2, 99);
        assertEquals(5, customArrayList.size());
        assertEquals(99, customArrayList.get(2));
        assertEquals(3, customArrayList.get(3));
    }

    @Test
    void testAddAtEnd() {
        customArrayList.add(99);
        assertEquals(5, customArrayList.size());
        assertEquals(99, customArrayList.get(4));
    }

    @Test
    void testAddAll() {
        customArrayList.addAll(Arrays.asList(5, 6, 7));
        assertEquals(7, customArrayList.size());
        assertEquals(7, customArrayList.get(6));
    }

    @Test
    void testClear() {
        customArrayList.clear();
        assertEquals(0, customArrayList.size());
        assertTrue(customArrayList.isEmpty());
    }

    @Test
    void testGet() {
        assertEquals(1, customArrayList.get(0));
        assertEquals(4, customArrayList.get(3));
    }

    @Test
    void testIsEmpty() {
        CustomArrayList<Integer> emptyList = new CustomArrayList<>(new Integer[]{});
        assertTrue(emptyList.isEmpty());

        assertFalse(customArrayList.isEmpty());
    }

    @Test
    void testRemoveByIndex() {
        customArrayList.remove(2);
        assertEquals(3, customArrayList.size());
        assertEquals(4, customArrayList.get(2));
    }

    @Test
    void testRemoveByObject() {
        customArrayList.remove((Integer) 2);
        assertEquals(3, customArrayList.size());
        assertEquals(3, customArrayList.get(1));
    }

    @Test
    void testSize() {
        assertEquals(4, customArrayList.size());

        customArrayList.add(99);
        assertEquals(5, customArrayList.size());
    }

    @Test
    void testSort() {
        customArrayList.addAll(Arrays.asList(9, 7, 6));
        customArrayList.sort(Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 3, 4, 6, 7, 9), Arrays.asList(customArrayList.get(0), customArrayList.get(1), customArrayList.get(2), customArrayList.get(3), customArrayList.get(4), customArrayList.get(5), customArrayList.get(6)));
    }
}
