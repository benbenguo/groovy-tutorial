package kit.utils

import java.beans.PropertyChangeListener

/**
 * Created by Dean on 2017/1/14.
 *
 * Groovy comes with observable lists, maps and sets.
 * Each of these collections trigger java.beans.PropertyChangeEvent events
 * when elements are added, removed or changed.
 * Note that a PropertyChangeEvent is not only signalling that a certain event has occurred, moreover,
 * it holds information on the property name and the old/new value a certain property has been changed to.
 */
class ObservableCollection {
    static main(args) {
        addedEvent()
//        clearedEvent()
    }

    /**
     * Depending on the type of change that has happened,
     * observable collections might fire more specialized PropertyChangeEvent types.
     * For example, adding an element to an observable list fires an ObservableList.ElementAddedEvent event.
     *
     * <ul>
     *     <li>Declares a PropertyChangeEventListener that is capturing the fired events</li>
     *     <li>ObservableList.ElementEvent and its descendant types are relevant for this listener</li>
     *     <li>Registers the listener</li>
     *     <li>Creates an ObservableList from the given list</li>
     *     <li>Triggers an ObservableList.ElementAddedEvent event</li>
     * </ul>
     */
    static addedEvent() {
        def event
        def listener = {
            if (it instanceof ObservableList.ElementEvent)  {
                event = it
            }
        } as PropertyChangeListener


        def observable = [1, 2, 3] as ObservableList
        observable.addPropertyChangeListener(listener)

        observable.add 42

        assert event instanceof ObservableList.ElementAddedEvent

        def elementAddedEvent = event as ObservableList.ElementAddedEvent
        assert elementAddedEvent.changeType == ObservableList.ChangeType.ADDED
        assert elementAddedEvent.index == 3
        assert elementAddedEvent.oldValue == null
        assert elementAddedEvent.newValue == 42
    }

    /**
     * The ObservableList.ElementClearedEvent event type is another interesting one.
     * Whenever multiple elements are removed,
     * for example when calling clear(), it holds the elements being removed from the list.
     */
    static clearedEvent() {
        def event
        def listener = {
            if (it instanceof ObservableList.ElementEvent)  {
                event = it
            }
        } as PropertyChangeListener


        def observable = [1, 2, 3] as ObservableList
        observable.addPropertyChangeListener(listener)

        observable.clear()

        assert event instanceof ObservableList.ElementClearedEvent

        def elementClearedEvent = event as ObservableList.ElementClearedEvent
        assert elementClearedEvent.values == [1, 2, 3]
        assert observable.size() == 0
    }
}
