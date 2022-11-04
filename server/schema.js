const Mongoose = require("mongoose");
const { Schema } = Mongoose;

const partSchema = new Schema({
  name: String,
  description: String,
  compatibilities: [String],
  imageUrl: [String],
});

const bicycleSchema = new Schema({
  name: String,
  imageUrl: [String],
});

const Part = Mongoose.model("Part", partSchema);
const Bicycle = Mongoose.model("Bicycle", bicycleSchema);
