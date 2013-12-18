/*
 * Copyright (C) 2013 Florian Frankenberger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lastdigitofpi.xdata.explorer;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;

/**
 *
 * @author Florian Frankenberger
 */
public class TypeWrapperModel extends DefaultComboBoxModel<Object>{

    public static abstract class TypeWrapper<T> {

        public abstract Class<T> getType();

        /**
         * if null is returned then the string is not valid
         *
         * @param string
         * @return
         */
        public abstract T wrap(String string);

        public String unWrap(T object) {
            return object.toString();
        }

        public boolean enableEditor() {
            return true;
        }

        @Override
        public String toString() {
            return getType().getSimpleName();
        }

        public abstract Icon getIcon();

    }

    public static final TypeWrapper[] TYPE_WRAPPERS = new TypeWrapper[]{
        new TypeWrapper<String>() {

            @Override
            public Class<String> getType() {
                return String.class;
            }

            @Override
            public String wrap(String string) {
                return string;
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_STRING;
            }

        },
        new TypeWrapper<Byte>() {

            @Override
            public Class<Byte> getType() {
                return Byte.class;
            }

            @Override
            public Byte wrap(String string) {
                try {
                    return Byte.parseByte(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }

        },
        new TypeWrapper<Short>() {

            @Override
            public Class<Short> getType() {
                return Short.class;
            }

            @Override
            public Short wrap(String string) {
                try {
                    return Short.parseShort(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Character>() {

            @Override
            public Class<Character> getType() {
                return Character.class;
            }

            @Override
            public Character wrap(String string) {
                try {
                    return Character.forDigit(Integer.valueOf(string), 10);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Integer>() {

            @Override
            public Class<Integer> getType() {
                return Integer.class;
            }

            @Override
            public Integer wrap(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Long>() {

            @Override
            public Class<Long> getType() {
                return Long.class;
            }

            @Override
            public Long wrap(String string) {
                try {
                    return Long.valueOf(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_INTEGER;
            }
        },
        new TypeWrapper<Float>() {

            @Override
            public Class<Float> getType() {
                return Float.class;
            }

            @Override
            public Float wrap(String string) {
                try {
                    return Float.valueOf(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_FLOAT;
            }
        },
        new TypeWrapper<Double>() {

            @Override
            public Class<Double> getType() {
                return Double.class;
            }

            @Override
            public Double wrap(String string) {
                try {
                    return Double.valueOf(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_FLOAT;
            }
        },
        new TypeWrapper<Boolean>() {

            @Override
            public Class<Boolean> getType() {
                return Boolean.class;
            }

            @Override
            public Boolean wrap(String string) {
                return Boolean.valueOf(string);
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_BOOLEAN;
            }
        },
        new TypeWrapper<Date>() {

            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            @Override
            public Class<Date> getType() {
                return Date.class;
            }

            @Override
            public Date wrap(String string) {
                try {
                    return dateFormat.parse(string);
                } catch (ParseException e) {
                    return null;
                }
            }

            @Override
            public String unWrap(Date object) {
                return dateFormat.format(object);
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_DATE;
            }

        },
        new TypeWrapper<URL>() {

            @Override
            public Class<URL> getType() {
                return URL.class;
            }

            @Override
            public URL wrap(String string) {
                try {
                    return new URL(string);
                } catch (MalformedURLException e) {
                    return null;
                }
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_URL;
            }

        },
        new TypeWrapper<Void>() {

            @Override
            public Class<Void> getType() {
                return Void.class;
            }

            @Override
            public Void wrap(String string) {
                return null;
            }

            @Override
            public String unWrap(Void object) {
                return "";
            }

            @Override
            public boolean enableEditor() {
                return false;
            }

            @Override
            public String toString() {
                return "Null";
            }

            @Override
            public Icon getIcon() {
                return DataNodeIconValue.ICON_NULL;
            }

        },
    };

    public TypeWrapperModel() {
        super(new Vector(Arrays.asList(TYPE_WRAPPERS)));
    }

}
