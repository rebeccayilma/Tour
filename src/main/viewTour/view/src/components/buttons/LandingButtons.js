export function ProposedActivitiesButton() {
    return (
        <div>
            <a href={this.seeProposedActivities()}>See proposed activities</a>
        </div>
    );
}

export function NewPlaceButton(addPlace) {
    return (
        <div>
           <a href={addPlace}>Add place</a>
        </div>
    );
}