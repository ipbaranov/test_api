package suiteTestExample;

import helperMethods.Calculate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class junitParameterized {

    @RunWith(Parameterized.class)
    public class RestApiDemo {
        private int code;
        private int country;
        private int expected;

        public RestApiDemo(int code, int countre, int expected) {
            this.code = code;
            this.country = countre;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "{index}:sumOf({2}-{2})={0}")
        public  Iterable<Object[]> dataForTest() {
            return Arrays.asList(new Object[][]{
                    {1, 1, 0},
                    {2, 6, 6},
                    {18, 2, 20},
                    {13, 15, 28},
                    {1, 5, 6}
            });
        }

        @Test
        public void paramTest() {
            assertEquals(expected, new Calculate().getSum(code,country));
        }
    }
}
