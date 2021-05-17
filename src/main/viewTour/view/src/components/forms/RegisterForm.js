import React, {useState} from "react";

export function RegisterForm(props) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const submit = props.submit;

    const handleSubmit = (evt) => {
        evt.preventDefault();

        if (!username) {
            // TODO: inform error
            // return this.setState({ error: 'Password is required' });
            return;
        }

        if (!password) {
            // TODO: inform error
            // return this.setState({ error: 'Password is required' });
            return;
        }

        submit(username, password);
    }

    const handleUserChange = (evt) => {
        setUsername(evt.target.value);
    }

    const handlePassChange = (evt) => {
        setPassword(evt.target.value);
    }

    return (
        <div className="Register">
            <br  />
            <br  />
            <div className="p-10">
                <h2 className="text-center text-3xl leading-9 font-extrabold text-gray-800">Register</h2>
                <p className="text-center text-sm leading-5 font-extrabold text-gray-600"></p>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="flex justify-center">
                    <div className="lg:w-1/3 md:w-2/3 w-full">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" >Username</label>
                        <input type="text" data-test="username" placeholder="Username" className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-teal-500 " value={username} onChange={handleUserChange} />
                    </div>
                </div>
                <div className="flex justify-center mt-4">
                    <div className="lg:w-1/3 md:w-2/3 w-full">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" htmlFor="password">Password</label>
                        <input type="password" data-test="password" placeholder="*********" className="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-teal-500 " value={password} onChange={handlePassChange} />
                    </div>
                </div>
                <div className="mt-4 flex justify-center">
                    <button type="submit" className="group w-full lg:w-1/3 md:w-2/3 py-2 px-4  border border-transparent text-sm leading-5 font-medium
                rounded-md text-white bg-blue-500 hover:bg-teal-400 focus:outline-none focus:border-teal-400
                focus:shadow-outline-teal active:bg-blue-400 active:outline-none transition duration-150 ease-in-out" data-test="submit"> Register </button>

                </div>
            </form>
        </div>
    );
}
