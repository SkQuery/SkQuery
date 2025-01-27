package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import com.skquery.skquery.SkQuery;
import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.elements.effects.base.Pragma;
import com.skquery.skquery.util.CancellableBukkitTask;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.*;

@Name("Safely Execute Thread")
@Description("Makes the next line of code run on a different thread. The code following it will be delayed until the thread finishes.")
@Patterns("$ thread")
public class EffOptionThread extends Pragma {

    private final ExecutorService staticThreadPool = Executors.newFixedThreadPool(10);
    private static Method walkMethod;

    static {
        try {
            walkMethod = TriggerItem.class.getDeclaredMethod("walk", Event.class);
            walkMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected TriggerItem walk(final Event e) {
        final Future<TriggerItem> toWalk = walkNext(getNext(), e);
        final CancellableBukkitTask task = new CancellableBukkitTask() {
            @Override
            public void run() {
                if (toWalk.isDone()) {
                    try {
                        TriggerItem.walk(toWalk.get(), e);
                    } catch (ExecutionException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        if (getNext() != null) TriggerItem.walk(getNext().getNext(), e);
                    }
                    cancel();
                }
            }
        };
        task.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SkQuery.getInstance(), task, 0, 1));
        return null;
    }

    public Future<TriggerItem> walkNext(final TriggerItem next, final Event e) {
        return staticThreadPool.submit(new Callable<TriggerItem>() {
            @Override
            public TriggerItem call() throws Exception {
                walkMethod.invoke(next, e);
                return next.getNext();
            }
        });
    }

    @Override
    protected void register(File executingScript, SkriptParser.ParseResult parseResult) {

    }
}
