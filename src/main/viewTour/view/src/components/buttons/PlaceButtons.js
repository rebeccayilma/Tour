export function NewActivityButton(props) {
    return (
        <div>
           <button className="group w-full lg:w-1/3 md:w-2/3 py-2 px-4  border border-transparent text-sm leading-5 font-medium
                rounded-md text-white bg-blue-500 hover:bg-teal-400 focus:outline-none focus:border-teal-400
                focus:shadow-outline-teal active:bg-blue-400 active:outline-none transition duration-150 ease-in-out" onClick={props.addActivity}>Add activity</button>
        </div>
    );
}
