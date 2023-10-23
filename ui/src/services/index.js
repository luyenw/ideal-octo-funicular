import axios from "axios";
const getRecentMessage = async (id) => {
    const url = `http://localhost:8080/recent/${id}`;
  try {
    const response = await axios.get(url);
    console.log(response.data)
    return response.data.sort((a, b)=>{
      return b.at-a.at
    })
  } catch (err) {
    console.log(err);
  }
  return []
};
const findUser = async(name)=>{
  const url = `http://localhost:3001/u/find/${encodeURI(name)}`
  try {
    const response = await axios.get(url);
    return response.data
  } catch (err) {
    console.log(err);
  }
  return []
}
export default { getRecentMessage, findUser };
