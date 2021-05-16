export function ProposedActivitiesButton(seeProposedActivities) {
    return (
        <div>
            <button onClick={() => seeProposedActivities()}>See proposed activities</button>
        </div>
    );
}

export function NewPlaceButton(addPlace) {
    return (
        <div>
           <button onClick={() => addPlace()}>Add place</button>
        </div>
    );
}