package ar.edu.ungs.fleet_manager.controls.domain;

import java.util.UUID;

public final class ControlTemplate {
    private final ControlId id;
    private ControlSubject subject;
    private ControlDescription description;
    private ControlPriority priority;
    private ControlAssigned assigned;

    public ControlTemplate(ControlId id, ControlSubject subject, ControlDescription description, ControlPriority priority, ControlAssigned assigned) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.assigned = assigned;
    }

    public static ControlTemplate create(String subject, String description, String priority, String assigned){

        return build(UUID.randomUUID().toString(),
                    subject,
                    description,
                    priority,
                    assigned);
    }


    public static ControlTemplate build(String id, String subject, String description, String priority, String assigned){

        return new ControlTemplate(new ControlId(id),
                                   new ControlSubject(subject),
                                   new ControlDescription(description),
                                   ControlPriority.parse(priority),
                                   new ControlAssigned(assigned));
    }

    public ControlId id() {
        return id;
    }

    public ControlSubject subject() {
        return subject;
    }

    public ControlDescription description() {
        return description;
    }

    public ControlPriority priority() {
        return priority;
    }

    public ControlAssigned assigned() {
        return assigned;
    }
}
