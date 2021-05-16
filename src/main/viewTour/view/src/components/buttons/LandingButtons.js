export function ProposedActivitiesButton(props) {
    return (
        <div>
            <button onClick={props.seeProposedActivities}>See proposed activities</button>
        </div>
    );
}

export function NewPlaceButton(props) {
    return (
        <div>
           <button onClick={props.addPlace}>Add place</button>
        </div>
    );
}