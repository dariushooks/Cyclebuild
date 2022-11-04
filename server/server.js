const Hapi = require("@hapi/hapi");
const Mongoose = require("mongoose");
const routes = require('./routes')

const server = new Hapi.server({
  host: "localhost",
  port: 3000,
  routes: {
    cors: {
      origin: ["*"],
      headers: ["Accept", "Content-Type", "Access-Control-Allow-Origin"],
      additionalHeaders: ["X-Requested-With"],
    },
  },
});

async function main() {
  await Mongoose.connect("mongodb://localhost/cyclebuild", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  });
  console.log("connection open");
}

main().catch((err) => console.log(err));

require('./import')

server.route(routes)

server.start();
