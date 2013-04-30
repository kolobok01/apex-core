/*
 *  Copyright (c) 2012-2013 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.malhartech.bufferserver.util;

import com.malhartech.netlet.DefaultEventLoop;
import com.malhartech.netlet.EventLoop;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Chetan Narsude <chetan@malhar-inc.com>
 */
public class System
{
  private static final HashMap<String, DefaultEventLoop> eventloops = new HashMap<String, DefaultEventLoop>();

  public static void startup(String identifier)
  {
    synchronized (eventloops) {
      DefaultEventLoop el = eventloops.get(identifier);
      if (el == null) {
        try {
          eventloops.put(identifier, el = new DefaultEventLoop(identifier));
        }
        catch (IOException io) {
          throw new RuntimeException(io);
        }
      }
      el.start();
    }
  }

  public static void shutdown(String identifier)
  {
    synchronized (eventloops) {
      DefaultEventLoop el = eventloops.get(identifier);
      if (el == null) {
        throw new RuntimeException("System with " + identifier + " not setup!");
      }
      else {
        el.stop();
      }
    }
  }

  public static EventLoop getEventLoop(String identifier)
  {
    synchronized (eventloops) {
      return eventloops.get(identifier);
    }
  }

}