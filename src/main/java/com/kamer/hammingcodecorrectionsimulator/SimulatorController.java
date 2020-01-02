package com.kamer.hammingcodecorrectionsimulator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on January, 2020
 *
 * @author kamer
 */
@Controller
@RequestMapping
public class SimulatorController {

	@GetMapping("/")
	public String index() {

		return "index";
	}

	@PostMapping("/")
	public String postInput(String inputText, Model model) {

		final String binaryInputText = Utils.convertTextToBinary(inputText);

		final String binaryInputTextInQuadlet = Utils.divideByNum(binaryInputText, 4);

		final String encodedText = Utils.encode(binaryInputText);

		final String encodedTextInSeven = Utils.divideByNum(encodedText, 7);

		final String corruptedBinary = Utils.corruptText(encodedText);

		final String corruptedBinaryInSeven = Utils.divideByNum(corruptedBinary, 7);

		final String corruptedText = Utils.convertToString(corruptedBinary);

		final List<Integer> errorIndexes = Utils.detect(corruptedBinary);

		final String correctedBinary = Utils.correct(corruptedBinary, errorIndexes);

		final String correctedBinaryInSeven = Utils.divideByNum(correctedBinary, 7);

		final String decodedBinary = Utils.decode(correctedBinary);

		final String decodedBinaryQuadlet = Utils.divideByNum(decodedBinary, 4);

		final String decodedText = Utils.convertToString(decodedBinary);

		model.addAttribute("inputText", inputText);

		model.addAttribute("binaryForm", binaryInputTextInQuadlet);

		model.addAttribute("encodedTextInSeven", encodedTextInSeven);

		model.addAttribute("corruptedText", corruptedText);

		model.addAttribute("errorIndexes", errorIndexes.stream().map(String::valueOf).collect(Collectors.joining(", ")));

		model.addAttribute("corruptedBinary", corruptedBinaryInSeven);

		model.addAttribute("correctedBinary", correctedBinaryInSeven);

		model.addAttribute("decodedBinary", decodedBinaryQuadlet);

		model.addAttribute("decodedText", decodedText);

		return "index";
	}

}
