/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.addthis.hydra.data.filter.value;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.addthis.codec.config.Configs.decodeObject;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.isA;

public class TestValueFilterError {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void type() throws IOException {
        ValueFilterError filter = decodeObject(ValueFilterError.class,
                                                "type: java.io.IOException, message: hello world");
        thrown.expectCause(isA(IOException.class));
        filter.filter(null);
    }

    @Test
    public void message() throws IOException {
        ValueFilterError filter = decodeObject(ValueFilterError.class, "message:hello world");
        thrown.expectMessage("hello world");
        filter.filter(null);
    }

    @SuppressWarnings("unchecked") @Test
    public void throwableTypeValidation() throws IOException {
        thrown.expect(anyOf(isA((Class) IllegalArgumentException.class),
                            isA((Class) JsonMappingException.class)));
        decodeObject(ValueFilterError.class, "message:hello world, type:blahblah");
    }

}
