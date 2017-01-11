/*
 * Copyright 2016 Su Baochen and individual contributors by the 
 * @authors tag. See the copyright.txt in the distribution for
 * a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.sdut.softlab.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import cn.edu.sdut.softlab.model.Item;
import cn.edu.sdut.softlab.service.ItemFacade;

@Named("itemManager")
@RequestScoped
public class ItemManagerImpl implements ItemManager {

  @Inject
  private transient Logger logger;
  @Inject 
  ItemFacade userService1;
  
  @Inject
  EntityManager em;
  
  @Inject
  private UserTransaction utx;

  private Item newItem = new Item();
  //代码设计的巧妙之处
  public Item getNewItem() {
    return newItem;
  }

  public void setNewItem(Item newItem) {
    this.newItem = newItem;
  }

  @Override
  @Produces
  @Named
  @RequestScoped
  public List<Item> getItems() throws Exception {
    try {
      utx.begin();
      return userService1.findAll();
    } finally {
      utx.commit();
    }
  }

  @Override
  public String addItems() throws Exception {
    try {
      utx.begin();
      userService1.create(newItem);
      logger.log(Level.INFO, "Added {0}", newItem);
      return "/success_register.xhtml?faces-redirect=true";
    } finally {
      utx.commit();
    }
  }


}
