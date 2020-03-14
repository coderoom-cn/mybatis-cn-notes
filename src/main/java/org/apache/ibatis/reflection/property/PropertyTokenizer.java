/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.reflection.property;

import java.util.Iterator;

/**
 * 属性分解器，属性分解为标记，迭代器模式 如person[0].birthdate.year，将依次取
 * 得person[0], birthdate, year
 * 字符串表达式解析封装类[字符串迭代器]
 * @author Clinton Begin
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
  //表达式名称
  private String name;
  //表达式的索引序名称
  private final String indexedName;
  //索引下标
  private String index;
  //子表达式
  private final String children;

  /**
   * 传入要解析的字符串表达式，例如“orders[0].items[0].name”
   * @param fullname
   */
  public PropertyTokenizer(String fullname) {
    int delim = fullname.indexOf('.');
    if (delim > -1) {
      name = fullname.substring(0, delim);
      children = fullname.substring(delim + 1);
    } else {
      name = fullname;
      children = null;
    }
    indexedName = name;
    delim = name.indexOf('[');
    if (delim > -1) {
      index = name.substring(delim + 1, name.length() - 1);
      name = name.substring(0, delim);
    }
  }

  /**
   * orders[0].items[0].name==>order
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * orders[0].items[0].name==>0(order后面那个0)
   * @return
   */
  public String getIndex() {
    return index;
  }

  /**
   * orders[0].items[0].name==>orders[0]
   * @return
   */
  public String getIndexedName() {
    return indexedName;
  }

  /**
   * orders[0].items[0].name==>items[0].name
   * orders[0].name==>name
   * @return
   */
  public String getChildren() {
    return children;
  }

  /**
   * 如'orders[0].items[0].name',其children就是items[0].name,如果children不为null返回true
   * @return
   */
  @Override
  public boolean hasNext() {
    return children != null;
  }

  /**
   *  创建新的PropertyTokenizer对象并解析children字段子表达式
   * @return
   */
  @Override
  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
  }
}
