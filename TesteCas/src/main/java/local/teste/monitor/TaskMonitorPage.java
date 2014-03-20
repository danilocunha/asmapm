package local.teste.monitor;

import java.util.Iterator;
import java.util.Map;

import local.teste.util.ObjectSizeFetcher;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class TaskMonitorPage extends WebPage {

	public TaskMonitorPage() {
		
		Iterator iter = TaskMonitor.getInstance().getHashMap().entrySet().iterator();

		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		int index = 0;
		while (iter.hasNext())
        {
            AbstractItem item = new AbstractItem(repeating.newChildId());
            Map.Entry mEntry = (Map.Entry) iter.next();
            repeating.add(item);
            

            //item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
            item.add(new Label("id", String.valueOf(mEntry.getKey())));
            item.add(new Label("call", String.valueOf(mEntry.getValue())));            
            

            final int idx = index;
            item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public String getObject()
                {
                    return (idx % 2 == 1) ? "even" : "odd";
                }
            }));

            index++;
            if(index==10) {
            	break;
            }
            
            
        }
		try {
			System.out.println(ObjectSizeFetcher.sizeOf(TaskMonitor.getInstance().getHashMap())/1024/1024);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
